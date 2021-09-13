package presenter

import com.ilustris.doitch.base.models.Group
import com.silent.ilustriscore.core.presenter.BasePresenter
import com.silent.ilustriscore.core.view.BaseView
import viewmodel.GroupModel

class GroupPresenter(override val view: BaseView<Group>): BasePresenter<Group>() {
    
    override val model = GroupModel(this)

}