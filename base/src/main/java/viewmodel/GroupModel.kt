package viewmodel

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ilustris.doitch.base.models.Group
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter

class GroupModel(presenter: BasePresenter<Group>): BaseModel<Group>(presenter) {
    override val path: String = "Group"

    override fun deserializeDataSnapshot(dataSnapshot: DocumentSnapshot): Group {
        return dataSnapshot.toObject(Group::class.java)!!
    }

    override fun deserializeDataSnapshot(dataSnapshot: QueryDocumentSnapshot): Group {
        return dataSnapshot.toObject(Group::class.java)
    }

}