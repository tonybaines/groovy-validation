package tonyb.groovy.validation

import java.util.ArrayList

@Immutable final class Outcome {
    Level level
    String message

    static def information(msg) { new Outcome(Level.Information, msg) }
    static def warning(msg) { new Outcome(Level.Warning, msg) }
    static def serious(msg) { new Outcome(Level.Serious, msg) }
    static def processingError(msg) { new Outcome(Level.ProcessingError, msg) }
}
