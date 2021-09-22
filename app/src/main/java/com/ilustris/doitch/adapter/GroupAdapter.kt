package com.ilustris.doitch.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.doitch.GroupActivity
import com.ilustris.doitch.R
import com.ilustris.doitch.base.models.*
import com.silent.ilustriscore.core.utilities.delayedFunction
import android.util.TypedValue
import com.ilustris.doitch.databinding.AboutHeaderLayoutBinding
import com.ilustris.doitch.databinding.CreateGroupCardBinding
import com.ilustris.doitch.databinding.GroupCardLayoutBinding
import com.ilustris.doitch.databinding.SocialCardLayoutBinding


private const val NEWGROUP = 0
private const val GROUP = 1
private const val HEADER = 2
private const val SOCIAL = 3

class GroupAdapter(
    val groupList: List<Group>,
    val createGroup: () -> Unit,
    val deleteGroup: (Group) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CreateGroupViewHolder(private val createGroupCardBinding: CreateGroupCardBinding) :
        RecyclerView.ViewHolder(createGroupCardBinding.root) {

        fun bind() {
            createGroupCardBinding.createGroupButton.setOnClickListener {
                createGroup.invoke()
            }
        }

    }

    inner class AboutHeaderViewHolder(private val aboutHeaderLayoutBinding: AboutHeaderLayoutBinding) :
        RecyclerView.ViewHolder(aboutHeaderLayoutBinding.root)

    inner class SocialCardViewHolder(private val socialCardLayoutBinding: SocialCardLayoutBinding) :
        RecyclerView.ViewHolder(socialCardLayoutBinding.root) {

        fun bind() {
            groupList[adapterPosition].run {
                setupSocialCard(this)
            }
        }

        private fun setupSocialCard(group: Group) {
            when (group.id) {
                FOLLOW_ON_TWITCH -> setupTwitchCard()
                FOLLOW_ON_INSTAGRAM -> setupInstaCard()
                FOLLOW_ON_GITHUB -> setupGithubCard()
                CHECK_ON_PLAYSTORE -> setupPlayStoreCard()
            }
        }

        private fun setupTwitchCard() {
            socialCardLayoutBinding.run {
                socialCard.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.material_purpleA700
                        )
                    )
                )
                socialIcon.setImageResource(R.drawable.ic_iconmonstr_twitch_1)
                socialCard.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/lilfall"))
                    itemView.context.startActivity(browserIntent)
                }
            }
        }

        private fun setupInstaCard() {
            socialCardLayoutBinding.run {
                socialCard.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.material_pinkA200
                        )
                    )
                )
                socialIcon.setImageResource(R.drawable.ic_iconmonstr_instagram_11)
                socialCard.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/lilfalln/"))
                    itemView.context.startActivity(browserIntent)
                }
            }
        }

        private fun setupGithubCard() {
            socialCardLayoutBinding.run {
                socialCard.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.material_bluegrey800
                        )
                    )
                )
                socialIcon.setImageResource(R.drawable.ic_iconmonstr_github_1)
                socialCard.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/CaioProgramming"))
                    itemView.context.startActivity(browserIntent)
                }
            }
        }

        private fun setupPlayStoreCard() {
            socialCardLayoutBinding.run {
                socialCard.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.material_tealA400
                        )
                    )
                )
                socialIcon.setImageResource(R.drawable.ic_iconmonstr_google_play_1)
                socialCard.setOnClickListener {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/dev?id=8106172357045720296")
                    )
                    itemView.context.startActivity(browserIntent)
                }
            }
        }
    }

    inner class GroupViewHolder(private val groupViewBinding: GroupCardLayoutBinding) :
        RecyclerView.ViewHolder(groupViewBinding.root) {

        fun bind() {
            groupList[adapterPosition].run {
                val value = TypedValue()
                itemView.context.theme.resolveAttribute(R.attr.secondaryColor, value, true)
                groupViewBinding.groupCard.setCardBackgroundColor(value.data)
                groupViewBinding.groupTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
                groupViewBinding.groupTitle.text = title
                groupViewBinding.taskCount.text = "${tasks.size} tarefas"
                groupViewBinding.groupCard.setOnClickListener {
                    GroupActivity.launchIntent(
                        itemView.context, this,
                        groupViewBinding.groupTitle
                    )
                }
                groupViewBinding.groupCard.setOnLongClickListener {
                    deleteGroup(this)
                    false
                }
                groupViewBinding.groupProgress.max = tasks.size
                delayedFunction {
                    groupViewBinding.groupProgress.setProgress(
                        tasks.filter { it.checked }.size,
                        true
                    )
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (groupList[position].isASocialCard()) return SOCIAL
        return when (groupList[position].id) {
            CREATE_NEW_GROUP_ID -> {
                NEWGROUP
            }
            ABOUT_HEADER -> {
                HEADER
            }
            else -> {
                GROUP
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWGROUP -> CreateGroupViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.create_group_card,
                    parent,
                    false
                )
            )
            HEADER -> AboutHeaderViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.about_header_layout,
                    parent,
                    false
                )
            )
            SOCIAL -> SocialCardViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.social_card_layout,
                    parent,
                    false
                )
            )
            else -> GroupViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.group_card_layout,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (groupList[position].isASocialCard()) {
            (holder as SocialCardViewHolder).bind()
        } else {
            when (groupList[position].id) {
                CREATE_NEW_GROUP_ID -> {
                    (holder as CreateGroupViewHolder).bind()
                }
                ABOUT_HEADER -> (holder as AboutHeaderViewHolder)
                else -> (holder as GroupViewHolder).bind()
            }
        }

    }

}