package forecasting.model;

public class SlidingTimeWindow {

    private int[] window;

    public SlidingTimeWindow(int[] window){
        this.window = window;
    }

    public int getValueAt(int index){
        return window[index];
    }

    public int getLength(){
        return window.length;
    }
}
