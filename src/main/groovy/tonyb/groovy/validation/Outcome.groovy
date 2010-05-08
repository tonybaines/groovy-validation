package tonyb.groovy.validation

@Immutable final class Outcome {
    Level level
    String message

    static def trivial(msg) { new Outcome(Level.Trivial, msg) }
    static def warning(msg) { new Outcome(Level.Warning, msg) }
    static def serious(msg) { new Outcome(Level.Serious, msg) }
    static def processingError(msg) { new Outcome(Level.ProcessingError, msg) }

    static def sort(outcomes) { outcomes.sort { a,b -> a.level <=> b.level } }

    // Useful predefined filters
    static def trivialities(outcomes) { outcomes.findAll {it.level == Level.Trivial} }
    static def warnings(outcomes) { outcomes.findAll {it.level == Level.Warning} }
    static def seriousThings(outcomes) { outcomes.findAll {it.level == Level.Serious} }
    static def processingErrors(outcomes) { outcomes.findAll {it.level == Level.ProcessingError} }

    static def lessThanSerious(outcomes) { outcomes.findAll {it.level < Level.Serious} }
    static def moreThanTrivial(outcomes) { outcomes.findAll {it.level > Level.Trivial} }
    static def ignoringProcessingErrors(outcomes) { outcomes.findAll {it.level != Level.ProcessingError} }
}
