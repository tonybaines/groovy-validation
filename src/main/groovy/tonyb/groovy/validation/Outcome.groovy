package tonyb.groovy.validation

@Immutable final class Outcome {
    Level level
    String message

    static def trivial(msg) { new Outcome(Level.Trivial, msg) }
    static def warning(msg) { new Outcome(Level.Warning, msg) }
    static def serious(msg) { new Outcome(Level.Serious, msg) }
}
