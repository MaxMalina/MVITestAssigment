package com.exam.githubbrowser.ui.feature.repo.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.exam.githubbrowser.R
import com.exam.githubbrowser.data.common.buildUrlIntent
import com.exam.githubbrowser.data.model.UserDetails
import com.exam.githubbrowser.data.model.buildUserDetailsPreview
import com.exam.githubbrowser.ui.feature.common.RoundedImage
import java.util.*

@Composable
fun ReposListHeader(userDetail: UserDetails) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingXSmall = dimensionResource(id = R.dimen.padding_xsmall)

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            ScoreSession(userDetail)

            Spacer(modifier = Modifier.size(paddingXSmall))

            UserDetailSession(userDetail)

            Spacer(modifier = Modifier.size(paddingMedium))

            ButtonsSession(userDetail)
        }

        Divider(
            thickness = paddingMedium,
        )
    }
}

@Composable
fun ScoreSession(userDetail: UserDetails) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedImage(
            url = userDetail.avatarUrl,
            placeholder = R.drawable.ic_launcher_foreground,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.avatar_size_large))
                .padding(end = dimensionResource(id = R.dimen.padding_medium))
        )

        ScoreItem(
            count = userDetail.publicRepos,
            description = stringResource(R.string.repos_score_title)
        )
        ScoreItem(
            count = userDetail.followers,
            description = stringResource(R.string.followers_score_title)
        )
        ScoreItem(
            count = userDetail.following,
            description = stringResource(R.string.following_score_title)
        )
    }
}

@Composable
fun UserDetailSession(userDetail: UserDetails) {
    Text(
        text = userDetail.name,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = userDetail.location ?: stringResource(R.string.location_not_defined),
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
fun ButtonsSession(userDetail: UserDetails) {
    // See all button
    val context = LocalContext.current
    val profileIntent = remember { buildUrlIntent(userDetail.htmlUrl) }
    val blogIntent = remember { buildUrlIntent(userDetail.blogUrl) }

    // View Blog
    val blogNotFoundDialog = remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            context.startActivity(profileIntent)
        }) {
            Text(text = stringResource(R.string.button_see_all_title))
        }

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))

        OutlinedButton(onClick = {
            if (userDetail.blogUrl.isEmpty()) {
                blogNotFoundDialog.value = true
            } else {
                context.startActivity(blogIntent)
            }
        }) {
            Text(text = stringResource(R.string.button_view_blog_title))
        }
    }

    if (blogNotFoundDialog.value) {
        AlertDialog(onDismissRequest = {
            blogNotFoundDialog.value = false
        },
            title = {
                Text(text = stringResource(R.string.blog_not_found_dialog_title))
            },
            text = {
                Text(text = stringResource(R.string.blog_not_found_dialog_text))
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.blog_not_found_dialog_confirm_button).uppercase(
                        Locale.getDefault()
                    )
                )
            })
    }
}

@Composable
fun ScoreItem(count: Int, description: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReposUserDetailPreview() {
    ReposListHeader(userDetail = buildUserDetailsPreview())
}
