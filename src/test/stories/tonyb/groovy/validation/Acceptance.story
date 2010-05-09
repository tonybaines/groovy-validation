package tonyb.groovy.validation

import static tonyb.groovy.validation.Validator.validator
import static tonyb.groovy.validation.Level.*
import static tonyb.groovy.validation.Rule.rule

description "A validation framework"
narrative "this string is required for now", {
 as a "developer who wants to make use of a Validation framework in Groovy"
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
    given "a complex set of rules", {
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