enum Color {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BOLD("\u001B[1m"),
    PINK("\u001B[95m");


    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}