package Inlämningsuppgift;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class RewriteMe {

    public Database database = new Database();
    public List<Question> questions = database.getQuestions();

    //Skriv en funktioner som returnerar hur många frågor det finns i databasen?
    public int getAmountOfQuestionsInDatabase(){
        return questions.stream().collect(Collectors.toList()).size();
    }

    //Hur många frågor finns i databasen för en viss, given kategori (som ges som inparameter)
    public int getAmountOfQuestionsForACertainCategory(Category category){
       return (int) questions.stream().filter(c -> c.getCategory().equals(category)).count();

    }

    //Skapa en lista innehållandes samtliga frågesträngar i databasen
    public List<String> getListOfAllQuestions(){
        return questions.stream().map(Question::getQuestionString).collect(Collectors.toList());
    }

    //Skapa en lista innehållandes samtliga frågesträngar där frågan tillhör en viss kategori
    //Kategorin ges som inparameter
    public List<String> getAllQuestionStringsBelongingACategory(Category category){
        return questions.stream().filter(c -> c.getCategory().equals(category)).map(Question::getQuestionString).collect(Collectors.toList());

    }

    //Skapa en lista av alla svarsalternativ, där varje svarsalternativ får förekomma
    // en och endast en gång i den lista som du ska returnera
    public List<String> getAllAnswerOptionsDistinct(){
        List<String> list = questions.stream().map(Question::getAllAnswers).flatMap(Collection::stream).collect(Collectors.toList());
        return list.stream().distinct().collect(Collectors.toList());

    }


    //Finns en viss sträng, given som inparameter, som svarsalternativ till någon fråga i vår databas?
    public boolean isThisAnAnswerOption(String answerCandidate){
        List<String> list = questions.stream().map(Question::getAllAnswers).flatMap(Collection::stream).collect(Collectors.toList());
        return list.stream().anyMatch(a -> a.equalsIgnoreCase(answerCandidate));

    }

    //Hur ofta förekommer ett visst svarsalternativ, givet som inparameter, i databasen
    public int getAnswerCandidateFrequncy(String answerCandidate){
        List<String> list = questions.stream().map(Question::getAllAnswers).flatMap(Collection::stream).collect(Collectors.toList());
        return (int)list.stream().filter(q -> q.equalsIgnoreCase(answerCandidate)).count();

    }

    //Skapa en Map där kategorierna är nycklar och värdena är en lista
    //av de frågesträngar som tillhör varje kategori
    public Map<Category, List<String>> getQuestionGroupedByCategory(){
      return questions.stream().collect((Collectors.groupingBy(Question::getCategory, Collectors.mapping(Question::getQuestionString, Collectors.toList()))));
    }

    //Skapa en funktion som hittar det svarsalternativ som har flest bokstäver, i en kategori, given som inparameter
    // OBS: Du måste använda Reduce!
    public String getLongestLettercountAnwerInAGivenCategory(Category c) {

        Map<Category, List<Question>> questionsByCategory = questions.stream().collect(groupingBy(Question::getCategory));
        Map<Category, List<String>> listOfAnswersByCategory = questionsByCategory.entrySet().stream().collect(Collectors.toMap
                (Map.Entry::getKey, v->v.getValue().stream().map(Question::getAllAnswers).flatMap(Collection::stream).collect(Collectors.toList())));
        for (Map.Entry<Category, List<String>> entry : listOfAnswersByCategory.entrySet()){
            if (c == entry.getKey())
             return entry.getValue().stream().reduce((s1, s2) -> s1.length() > s2.length()? s1:s2).map(Object::toString).orElse("");
            }
        return null;

        }

    public static void main(String[] args){
        RewriteMe uppg4 = new RewriteMe();

    }

}

