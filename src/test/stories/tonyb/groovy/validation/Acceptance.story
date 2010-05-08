package tonyb.groovy.validation

import static tonyb.groovy.validation.Validator.validator
import static tonyb.groovy.validation.Level.*

description "A validation framework"
narrative "this string is required for now", {
 as a "developer who wants to make use of the Spring Validation framework in Groovy"
 i want "to be able to use a more Groovy API"
 so that "it's easy to validate stuff with reusable rules"
}

scenario "Using the validation framework in the simplest way possible",{
    given "a really simple validation definition",{
        definition = validator.withRules()
    }
    when "a context is evaluated", {
        context = [:]
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 0
    }
}
scenario "Using the validation framework with a simple rule",{
    given "a simple validation definition",{
        definition = validator.withRule(new Rule(
                                outcome: new Outcome(
                                        level: Trivial,
                                        message: "Knife + fork on the wrong sides of the plate"),
                                test: {context -> false}))
    }
    when "a context is evaluated", {
        context = [:]
        result = definition.validate(context)
    }
    then "meaningful validation results should be produced", {
        result.size.shouldBe 1
        result.shouldHave new Outcome(level: Trivial, message: "Knife + fork on the wrong sides of the plate")
    }
}