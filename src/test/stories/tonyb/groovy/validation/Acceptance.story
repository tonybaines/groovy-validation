package tonyb.groovy.validation

import static tonyb.groovy.validation.Validator.validator
import static tonyb.groovy.validation.Level.*
import static tonyb.groovy.validation.Rule.rule

description "A validation framework"
narrative "this string is required for now", {
 as a "developer who wants to make use of a validation framework in Groovy"
 i want "to be able to use a more Groovy API"
 so that "it's easy to validate stuff with reusable rules"
}

scenario "Using the validation framework in the simplest way possible",{
    given "a really simple validation definition",{
        definition = validator.withRules([])
    }
    when "a context is evaluated", {
        context = null
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 0
    }
}
scenario "Using the validation framework with a simple rule",{
    given "a simple validation definition",{
        rules = [rule({Outcome.information "Broken"})]
        definition = validator.withRules(rules)
    }
    when "a context is evaluated", {
        context = null
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 1
        result.shouldHave Outcome.information("Broken")
    }
}
scenario "Using the validation framework with more than one rule",{
    given "a validation definition",{
        definition = validator.andRule({Outcome.information "Broken"})
                              .andRule({Outcome.serious "Really Broken"})
    }
    when "a context is evaluated", {
        context = null
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 2
        result.shouldHave Outcome.information("Broken")
        result.shouldHave Outcome.serious("Really Broken")
    }
}
scenario "Using the validation framework with filtering operations", {
    given "a large set of rules", {
        myRules = []
        (1..5).each { i -> myRules << rule {Outcome.information "Info $i"} }
        (6..10).each { i -> myRules << rule {Outcome.warning "Warning $i"} }
        (11..15).each { i -> myRules << rule {Outcome.serious "Serious $i"} }
        (16..20).each { i -> myRules << rule {Outcome.processingError "Error $i"} }
        definition = validator.withRules(myRules)
    }
    when "a context is evaluated", {
        context = null
        result = definition.validate(context)
    }
    then "the outcomes can be processed with useful filters", {
        result.size.shouldBe 20
        result.moreThanInformation.ignoringProcessingErrors.size.shouldBe 10
        result.onlySerious.size.shouldBe 5
        result.lessThanSerious.size.shouldBe 10
    }
}

scenario "Using the validation framework with non-trivial rules", {
    given "a complex set of rules", {
        recentBuild = rule { c ->
            if (c.lastBuild < (new Date()-7)) Outcome.warning("No build for ${c.name} in the last 7 days")
            else Outcome.information("${c.name} has recent builds")
        }
        workingBuilds = rule { c ->
            failures = 0
            c.testResultRuns[0..4].each { (it == 'PASS') ? null : failures++ }
            if (failures >= 3) Outcome.serious("${c.name} has $failures failed builds in the last 5")
            else Outcome.information("${c.name} has passing builds")
        }

        definition = validator.andRule(recentBuild).andRule(workingBuilds)
    }
    when "a context is evaluated", {
        context1 = new Component(name: 'CIT Dashboard', lastBuild: (new Date()-8), testResultRuns: ['FAIL', 'FAIL', 'FAIL', 'PASS', 'PASS'])
        result1 = definition.validate(context1)
        context2 = new Component(name: 'build-stats-collector', lastBuild: new Date(), testResultRuns: ['FAIL', 'PASS', 'FAIL', 'PASS', 'PASS'])
        result2 = definition.validate(context2)
    }
    then "useful results can be extracted", {
        result1.shouldHave Outcome.warning("No build for CIT Dashboard in the last 7 days")
        result1.shouldHave Outcome.serious("CIT Dashboard has 3 failed builds in the last 5")
        result2.shouldHave Outcome.information("build-stats-collector has passing builds")
        result2.shouldHave Outcome.information("build-stats-collector has recent builds")
        result2.moreThanInformation.size.shouldBe 0
    }
}