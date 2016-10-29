package story;

/**
 * Created by sreenath on 10.06.15.
 */
public class Chapter {

    public int chapterIndex;
    public int character;

    public Chapter(int character, int chapterIndex) {
        this.chapterIndex = chapterIndex;
        this.character = character;
    }

    public int getHashCode() {
        return chapterIndex + (100*character);
    }
}
