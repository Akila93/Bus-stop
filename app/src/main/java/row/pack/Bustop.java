package row.pack;

public class Bustop extends Item{
    
    private String name;
    private boolean selected;
    
    public Bustop(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
