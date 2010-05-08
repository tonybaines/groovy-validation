package tonyb.groovy.validation

@Immutable final class Outcome {
    Level level
    String message

    static def information(msg) { new Outcome(Level.Information, msg) }
    static def warning(msg) { new Outcome(Level.Warning, msg) }
    static def serious(msg) { new Outcome(Level.Serious, msg) }
    static def processingError(msg) { new Outcome(Level.ProcessingError, msg) }

    static def sort(outcomes) { outcomes.sort { a,b -> a.level <=> b.level } }

    // Useful predefined filters
    static def informational(outcomes) { outcomes.findAll {it.level == Level.Information} }
    static def warnings(outcomes) { outcomes.findAll {it.level == Level.Warning} }
    static def seriousThings(outcomes) { outcomes.findAll {it.level == Level.Serious} }
    static def processingErrors(outcomes) { outcomes.findAll {it.level == Level.ProcessingError} }

    static def lessThanSerious(outcomes) { outcomes.findAll {it.level < Level.Serious} }
    static def moreThanInformation(outcomes) { outcomes.findAll {it.level > Level.Information} }
    static def ignoringProcessingErrors(outcomes) { outcomes.findAll {it.level != Level.ProcessingError} }
}
