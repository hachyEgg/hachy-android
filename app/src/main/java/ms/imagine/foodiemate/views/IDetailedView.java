package ms.imagine.foodiemate.views;

public interface IDetailedView{
    void toast(String st);
    void showProgress(Boolean show);
    void updateStatus(String state);
}