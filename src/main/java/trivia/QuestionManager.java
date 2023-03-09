package trivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class QuestionManager {
    public static final int QUESTIONS_COUNT = 50;
    private Map<Category, Queue<String>> questions = new HashMap<>();

    public QuestionManager() {
        initializeQuestions();
    }

    private void initializeQuestions() {
        for (Category category : Category.values()) {
            Queue<String> categoryQuestions = new LinkedList<>();
            for (int i = 0; i < QUESTIONS_COUNT; i++) {
                categoryQuestions.add(category.getName() + " Question " + i);
            }
            questions.put(category, categoryQuestions);
        }
    }

    public String getQuestionForCategory(Category category) {
        return questions.get(category).remove();
    }
}
