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
    def andRule(rule) {
        this.rules << rule
        this
    }

    def validate(context) {
        rules.collect { it.examine(context) }
    }
}
