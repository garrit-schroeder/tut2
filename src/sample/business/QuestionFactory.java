package sample.business;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class QuestionFactory {

    static int level;
    static int[] usedQuestions = new int[15];
    static int counter = 0;
    static final List<Question> questions = Arrays.asList(
            new Question("Peter Kreuder komponierte 1936 den Schlager 'Ich wollt ich waer ein...'", 1, 1, Arrays.asList(
                    new Answer("Hund", false), new Answer("Huhn", true), new Answer("Hummer", false), new Answer("Huflattich", false))),
            new Question("Wobei handelt es sich um ein Notsignal im internationalen Funkverkehr?", 2, 1, Arrays.asList(
                    new Answer("Mayday", true), new Answer("Down Town", false), new Answer("Jetset", false), new Answer("Flower Power", false))),
            new Question("Wie heisst - laut einem Maerchen der Brueder Grimm - die Schwester von Schneeweisschen?", 3, 1, Arrays.asList(
                    new Answer("Fliederlila", false), new Answer("Maisgelb", false), new Answer("Kornblumenblau", false), new Answer("Rosenrot", true))),
            new Question("Wer hat von Berufs wegen mit dem Spritzenhaus zu tun?", 4, 2, Arrays.asList(
                    new Answer("Gaertner", false), new Answer("Krankenschwester", false), new Answer("Feuerwehrmann", true), new Answer("Konditor", false))),
            new Question("Was ist die Amtssprache von Mexiko?", 5, 2, Arrays.asList(
                    new Answer("Portugiesisch", false), new Answer("Spanisch", true), new Answer("Englisch", false), new Answer("Franzoesich", false))),
            new Question("Unter welchem Namen sangen Wigald Boning und Olli Dittrich 'Lieder, die die Welt nicht braucht'?", 6, 2, Arrays.asList(
                    new Answer("Die Doofen", true), new Answer("Die Behaemmerten", false), new Answer("Die Abgedrehten", false), new Answer("Die Irren", false))),
            new Question("Was ist ein Rebus?", 7, 3, Arrays.asList(
                    new Answer("Kreditinstitut", false), new Answer("Werkzeug", false), new Answer("Weinstock", false), new Answer("Bilderraetsel", true))),
            new Question("Wie nennt man von Gletschern transportierten Gesteinsschutt?", 8, 3, Arrays.asList(
                    new Answer("Muraene", false), new Answer("Morelle", false), new Answer("Moraene", true), new Answer("Murnau", false))),
            new Question("Welches deutsche Fuerstenhaus organisierte bis ins 19. Jahrhundert sie kaiserliche Reichspost?", 9, 3, Arrays.asList(
                    new Answer("Fuerstenberg", false), new Answer("Thurn und Taxis", true), new Answer("Schaumburg-Lippe", false), new Answer("Hohenlohe", false))),
            new Question("Wobei handelt es sich nicht um ein Insekt?", 10, 4, Arrays.asList(
                    new Answer("Grasmuecke", true), new Answer("Stechmuecke", false), new Answer("Wintermuecke", false), new Answer("Kriebelmuecke", false))),
            new Question("Wie heisst das traditionelle Pferderennen in Siena?", 11, 4, Arrays.asList(
                    new Answer("Boccia", false), new Answer("Barolo", false), new Answer("Calcio", false), new Answer("Palio", true))),
            new Question("Was ist nach Mahón, dem Hauptort der Insel Menorca, benannt?", 12, 4, Arrays.asList(
                    new Answer("Marone", false), new Answer("Mahagoni", false), new Answer("Mayonnaise", true), new Answer("Marihuana", false))),
            new Question("Welcher deutsche Boxer schlug im Juni 1952 den Ringrichter Max Pippow zu Boden?", 13, 5, Arrays.asList(
                    new Answer("Eckhard Dagge", false), new Answer("Peter Mueller", true), new Answer("Bubi Scholz", false), new Answer("Max Schmeling", false))),
            new Question("Wo befindet sich der Hauptsitz der UNESCO?", 14, 5, Arrays.asList(
                    new Answer("Paris", true), new Answer("Bruessel", false), new Answer("London", false), new Answer("Helsinki", false))),
            new Question("Mit wem stand Edmund Hillary 1953 auf dem Gipfel des Mount Everest?", 15, 5, Arrays.asList(
                    new Answer("Nasreddin Hodscha", false), new Answer("Nursay Pimsorn", false), new Answer("Abrindranath Singh", false), new Answer("Tenzing Norgay", true))),
            new Question("Wenn das Wetter gut ist, wird der Bauer bestimmt den Eber, das Ferkel und...", 16, 1, Arrays.asList(
                    new Answer("einen draufmachen", false), new Answer("die Nacht durchzechen", false), new Answer("die Sau rauslassen", true), new Answer("auf die Kacke hauen", false))),
            new Question("Was ist meisst ziemlich viel?", 17, 1, Arrays.asList(
                    new Answer("selbstbewusste Differenz", false), new Answer("arroganter Quotient", false), new Answer("stolze Summe", true), new Answer("hochmutiges Produkt", false))),
            new Question("Wessen Genesung schnell voranschreitet, der erholt sich...", 18, 1, Arrays.asList(
                    new Answer("hinguckends", false), new Answer("anschauends", false), new Answer("zusehends", true), new Answer("glotzends", false))),
            new Question("Natuerlich spielten musikalische Menschen auch im...", 19, 2, Arrays.asList(
                    new Answer("Westsaxo Phon", false), new Answer("Nordklari Nette", false), new Answer("Ostblock Floete", true), new Answer("Suedpo Saune", false))),
            new Question("Wo gibt es keine geregelten Oeffnungszeiten?", 20, 2, Arrays.asList(
                    new Answer("Baumaerkte", false), new Answer("Moebelhaeuser", false), new Answer("Fensterlaeden", true), new Answer("Teppichgeschaefte", false))),
            new Question("Was war bereits seit Mai 1969 ein beliebtes Zahlungsmittel im europaeischen Raum?", 21, 2, Arrays.asList(
                    new Answer("Euronoten", false), new Answer("Euroscheine", false), new Answer("Eurocheques", true), new Answer("Euromuenzen", false))),
            new Question("Malu Dreyer profitierte Anfang des Jahres von...", 22, 3, Arrays.asList(
                    new Answer("Oettingers Sattelstange", false), new Answer("Veltins Fahrradkette", false), new Answer("Becks Ruecktritt", true), new Answer("Diebels Vorderreifen", false))),
            new Question("Woraus besteht in der Regel eine Entourage?", 23, 3, Arrays.asList(
                    new Answer("Baguette & Rotwein", false), new Answer("Mascara & Lidschatten", false), new Answer("Freunde & Bekannte", true), new Answer("Sofa & Sessel", false))),
            new Question("Was haben die Hollywood-Stars Gosling, Reynolds und Phillippe gemeinsam?", 24, 3, Arrays.asList(
                    new Answer("Ex-Frau Megan Fox", false), new Answer("Geburtsjahr 1978", false), new Answer("Vorname Ryan", true), new Answer("irische Staatsbuergerschaft", false))),
            new Question("Welche beiden Staaten einigten sich 2012 ueber die Festsetzung eines Grenzverlaufs?", 25, 4, Arrays.asList(
                    new Answer("Deutschland & Australien", false), new Answer("Polen & Suedafrika", false), new Answer("Daenemark & Kanada", true), new Answer("Oesterreich & Japan", false))),
            new Question("Seine drei Weltmeister-Titel erfuhr sich Sebastian Vettel mit Motoren von...", 26, 4, Arrays.asList(
                    new Answer("Ferrari", false), new Answer("Mercedes", false), new Answer("Renault", true), new Answer("Toyota", false))),
            new Question("Welcher General vertrieb im 19. Jahrhundert die Mexikaner aus dem heutigen US-Bundesstaat Texas?", 27, 4, Arrays.asList(
                    new Answer("John Denver", false), new Answer("Michael Miami", false), new Answer("Sam Houston", true), new Answer("Phil A. Delphia", false))),
            new Question("Der Text welches dieser beruehmten Songs ist ganz offensichtlich an eine Prostituierte gerichtet?", 28, 5, Arrays.asList(
                    new Answer("'Angie' von den Stones", false), new Answer("Manilows 'Mandy'", false), new Answer("'Roxanne' von The Police", true), new Answer("Jacksons 'Billie Jean'", false))),
            new Question("Was soll in bestimmten Abstaenden nach der sogenannten ABCDE-Regel kontrolliert werden?", 29, 5, Arrays.asList(
                    new Answer("Komposthaufen im Garten", false), new Answer("Luftdruck der Autoreifen", false), new Answer("Leberflecken auf der Haut", true), new Answer("Aktienfonds bei der Bank", false))),
            new Question("Wer sollte sich mit der 'Zwanzig nach vier'-Stellung auskennen?", 30, 5, Arrays.asList(
                    new Answer("Fahrlehrer", false), new Answer("Karatemeister", false), new Answer("Kellner", true), new Answer("Landschaftsarchitekt", false)))

    );

    private QuestionFactory() {
        super();
    }

    public static Question getRandomQuestion(int questionNumber) {


        if (questionNumber <= 3) {
            level = 1;
        } else if (questionNumber <= 6) {
            level = 2;
        } else if (questionNumber <= 9) {
            level = 3;
        } else if (questionNumber <= 12) {
            level = 4;
        } else {
            level = 5;
        }

        List<Question> currentLevelQuestions = questions.stream()
                .filter(question -> question.getLevel() == level)
                .collect(Collectors.toList());


        int random = new Random().nextInt(currentLevelQuestions.size());
        for (int i = 0; i < usedQuestions.length; i++) {
            if (currentLevelQuestions.get(random).getId() == usedQuestions[i]) {
                random = new Random().nextInt(currentLevelQuestions.size());
                i = -1;
            }
        }
        usedQuestions[counter] = currentLevelQuestions.get(random).getId();
        counter++;
        return currentLevelQuestions.get(random);

    }

    public static void restart() {
        Arrays.fill(usedQuestions, 0);
        counter = 0;
    }

    public static int getLevel() {
        return level;
    }
}