package tonyb.groovy.validation

@Immutable final class Rule {
    Outcome outcome
    Closure test
    def evaluate(context) {
      test.call(context) ? null : outcome
    }
}
