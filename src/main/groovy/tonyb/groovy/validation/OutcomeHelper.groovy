package tonyb.groovy.validation;

class OutcomeHelper {
    static ArrayList<Outcome> sort(ArrayList<Outcome> outcomes) {
        outcomes.sort { a,b -> a.level <=> b.level }
    }

    static ArrayList<Outcome> onlyInformation(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.Information}
    }

    static ArrayList<Outcome>  onlyWarnings(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.Warning}
    }

    static ArrayList<Outcome>  onlySerious(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.Serious}
    }

    static ArrayList<Outcome>  onlyProcessingErrors(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level == Level.ProcessingError} }

    static ArrayList<Outcome>  lessThanSerious(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level < Level.Serious}
    }

    static ArrayList<Outcome>  moreThanInformation(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level > Level.Information}
    }

    static ArrayList<Outcome>  ignoringProcessingErrors(ArrayList<Outcome> outcomes) {
        outcomes.findAll {it.level != Level.ProcessingError}
    }
}
