package ms.imagine.foodiemate.views;

public interface IDetailedView{
    void toast(String st);
    void showProgress(boolean show);
    void updateStatus(String state);
}