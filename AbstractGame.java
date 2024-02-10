import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGame implements Game {
    abstract List<String> generateCharList();

    private String word;
    private List<String> history;
    private Integer tryCount;
    private GameStatus gameStatus;

    public AbstractGame() {
        this.history = new ArrayList<>();
        this.gameStatus = GameStatus.INIT;
    }

    @Override
    public void start(Integer wordSize, Integer tryCount) {
        word = generateWord(wordSize);
        this.tryCount = tryCount;
        gameStatus = GameStatus.START;
        history.clear();
    }

    private String generateWord(Integer wordSize) {
        List<String> alphabet = generateCharList();
        Collections.shuffle(alphabet);
        return String.join("", alphabet.subList(0, wordSize));
    }

    @Override
    public Answer inputValue(String value) {
        if (!getGameStatus().equals(GameStatus.START)) {
            throw new RuntimeException("The game is not running.");
        }

        int cowCounter = 0;
        int bullCounter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (extracted(value).charAt(i) == word.charAt(i)) {
                cowCounter++;
                bullCounter++;
            } else if (word.contains(String.valueOf(extracted(value).charAt(i))) && Collections.frequency(history, String.valueOf(extracted(value).charAt(i))) < Collections.frequency(word.chars().mapToObj(e -> String.valueOf((char)e)).collect(Collectors.toList()), String.valueOf(extracted(value).charAt(i)))) {
            cowCounter++;
            }
        }
        tryCount--;
        if (tryCount == 0) {
            gameStatus = GameStatus.LOOSE;
        }
        if (bullCounter == word.length()) {
            gameStatus = GameStatus.WIN;
        }
        history.add(extracted(value) + " - " + cowCounter + " cows " + (bullCounter - cowCounter) + " bulls"); // log the guess
        return new Answer(cowCounter, bullCounter, tryCount);
    }

    private String extracted(String value) {
        return value;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public List<String> getGameHistory() {
        return new ArrayList<>(history);
    }

    public void restart() {
        this.gameStatus = GameStatus.INIT;
    }
}