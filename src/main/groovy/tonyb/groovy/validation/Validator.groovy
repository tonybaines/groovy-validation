package tonyb.groovy.validation

import static tonyb.groovy.validation.Rule.rule

class Validator {
    private rules = []
    private Validator() {}

    public static Validator getValidator() {
        new Validator()
    }

    def withRules(rules) {
        this.rules = rules
        this
    }
    def andRule(Rule newRule) {
        this.rules << newRule
        this
    }
    def andRule(Closure closure) {
        this.rules << rule(closure)
        this
    }

    def validate(context) {
        rules.collect { it.examine(context) }
    }
}
