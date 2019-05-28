public enum Difficulty_level {
    EASY(10),
    NORMAL(5),
    HARD(1);

    protected int slow_down_factor;

    Difficulty_level( int slow_down_factor) {
        this.slow_down_factor = slow_down_factor;
    }
}
