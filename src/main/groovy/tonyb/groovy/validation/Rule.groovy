package tonyb.groovy.validation

@Immutable final class Rule {
    Closure test
    private Rule(Closure test) { this.test = test }
    public static Rule rule(Closure test) {
        new Rule(test)
    }
    def examine(context) {
      test.call(context)
    }
}
