package tonyb.groovy.validation;

@Category(ArrayList)
class OutcomeHelper {
    static ArrayList<Outcome> getSorted(ArrayList<Outcome> outcomes) {
        outcomes.sort { a,b -> a.level <=> b.level }
    }

    static ArrayList<Outcome> getOnlyInformation(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.Information}
    }

    static ArrayList<Outcome> getOnlyWarnings(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.Warning}
    }

    static ArrayList<Outcome> getOnlySerious(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.Serious}
    }

    static ArrayList<Outcome> getOnlyProcessingErrors(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.ProcessingError} }

    static ArrayList<Outcome> getLessThanSerious(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level < Level.Serious}
    }

    static ArrayList<Outcome> getMoreThanInformation(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level > Level.Information}
    }

    static ArrayList<Outcome> getIgnoringProcessingErrors(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level != Level.ProcessingError}
    }
}
