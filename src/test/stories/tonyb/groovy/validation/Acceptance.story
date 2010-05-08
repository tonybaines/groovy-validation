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
        rules = [rule({Outcome.trivial "Broken"})]
        definition = validator.withRules(rules)
    }
    when "a context is evaluated", {
        context = null
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 1
        result.shouldHave Outcome.trivial("Broken")
    }
}
scenario "Using the validation framework with more than one rule",{
    given "a validation definition",{
        definition = validator.andRule({Outcome.trivial "Broken"})
                              .andRule({Outcome.serious "Really Broken"})
    }
    when "a context is evaluated", {
        context = null
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 2
        result.shouldHave Outcome.trivial("Broken")
        result.shouldHave Outcome.serious("Really Broken")
    }
}