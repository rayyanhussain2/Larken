package duoforge.larken;

public class SoundFX extends Audio{
    public SoundFX(String s, double V) {
        super(s);
        super.setVolume(V);
    }

    @Override
    public void run() {
        super.play();
    }
}
