package duoforge.larken;

public class BGM extends Audio{
    public BGM(String s, double V) {
        super(s);
        super.setVolume(V);
    }

    @Override
    public void run() {
        //loop as well
        super.setLoop();

    }
}
