package tonyb.groovy.validation

@Immutable final class Rule {
    Closure test
    public static rule(Closure test) {
        new Rule(test)
    }
    def Outcome examine(context) {
      try {
          test.call(context)
      } catch (Throwable e) {
          Outcome.processingError e.message
      }
    }
}
