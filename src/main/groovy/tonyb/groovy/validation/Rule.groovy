package tonyb.groovy.validation

@Immutable final class Rule {
    Closure test
    public static rule(Closure test) {
        new Rule(test)
    }
    def Outcome examine(Object context) { tryToCall {test(context)} }

    // Convenience methods for more than one context object
    def Outcome examine(a,b) { tryToCall { test(a,b) } }
    def Outcome examine(a,b,c) { tryToCall { test(a,b,c) } }
    def Outcome examine(a,b,c,d) { tryToCall { test(a,b,c,d) } }
    def Outcome examine(a,b,c,d,e) { tryToCall { test(a,b,c,d,e) } }

    // More arguments need to be treated as an array
    def Outcome examine(Object[] context) { tryToCall {test(context) } }

    /*
     * Execute the supplied closure, trapping any exceptions
     */
    private def tryToCall(Closure c) {
        try {
            c.call()
        } catch (Throwable e) {
            Outcome.processingError e.message
        }
    }
}
