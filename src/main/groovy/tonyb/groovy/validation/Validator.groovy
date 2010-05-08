package tonyb.groovy.validation

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
    def withRule(rule) {
        this.rules << rule
        this
    }

    def validate(context) {
        def outcomes = []
        rules.each {
            outcomes << it.evaluate(context)
        }
        outcomes
    }
}
