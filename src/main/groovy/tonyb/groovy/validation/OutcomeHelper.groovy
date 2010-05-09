package tonyb.groovy.validation;

@Category(ArrayList)
class OutcomeHelper {

    static def getSorted(ArrayList<Outcome> outcomes) {
        mixinSelf { outcomes.sort { a,b -> a.level <=> b.level } }
    }

    static def getOnlyInformation(ArrayList<Outcome> outcomes) {
        filterByLevel(outcomes, Level.Information)
    }
    static def getOnlyWarnings(ArrayList<Outcome> outcomes) {
        filterByLevel(outcomes, Level.Warning)
    }
    static def getOnlySerious(ArrayList<Outcome> outcomes) {
        filterByLevel(outcomes, Level.Serious)
    }
    static def getOnlyProcessingErrors(ArrayList<Outcome> outcomes) {
        filterByLevel(outcomes, Level.ProcessingError)
    }

    static def getLessThanSerious(ArrayList<Outcome> outcomes) {
        findAllBy outcomes, {it.level < Level.Serious}
    }
    static def getMoreThanInformation(ArrayList<Outcome> outcomes) {
        findAllBy outcomes, {it.level > Level.Information}
    }
    static def getIgnoringProcessingErrors(ArrayList<Outcome> outcomes) {
        findAllBy outcomes, {it.level != Level.ProcessingError}
    }



    private static def filterByLevel(outcomes, level) {
        findAllBy outcomes, {it.level == level}
    }
    private static def findAllBy(outcomes, Closure criterium) {
        mixinSelf { outcomes.findAll(criterium) }
    }

    /*
     * Necessary to allow pipelines of filters
     */
    private static mixinSelf(Closure f) {
        def result = f.call()
        result.metaClass.mixin OutcomeHelper
        result
    }
}
