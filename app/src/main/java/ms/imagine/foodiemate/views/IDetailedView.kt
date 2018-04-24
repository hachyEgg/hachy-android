package ms.imagine.foodiemate.views

import android.widget.ImageView

interface IDetailedView{
    fun toast(st: String)
    fun showProgress(show: Boolean)
    fun updateStatus(state: String)
}