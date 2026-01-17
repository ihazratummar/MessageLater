package com.hazrat.create_reminder.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.hazrat.common.domain.model.RepeatOption
import com.hazrat.contacts.Contact
import com.hazrat.create_reminder.ui.CreateReminderStates
import com.hazrat.ui.AppDimens
import com.hazrat.ui.MessageLaterTheme


/**
 * @author hazratummar
 * Created on 11/01/26
 */

@Composable
fun ContactInputField(
    contactName: String = "",
    contactPhoneNumber: String = "",
    error: String? = null,
    onClick: () -> Unit = {},
    onClear: () -> Unit = {}
) {
    val isFilled = contactName.isNotBlank()

    Column {
        Text(
            text = "Who to message?",
            style =
                MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = AppDimens.spacingXs)
        )

        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(AppDimens.radiusMd),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = if (isFilled) AppDimens.elevationSm / 2 else AppDimens.elevationNone,
            border = BorderStroke(
                width = AppDimens.borderWidth,
                color =
                    when {
                        error != null ->
                            MaterialTheme.colorScheme.error

                        isFilled ->
                            MaterialTheme.colorScheme
                                .outlineVariant

                        else -> MaterialTheme.colorScheme.outline
                    }
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier =
                    Modifier.fillMaxWidth()
                        .heightIn(min = if (isFilled) AppDimens.spacingXxl * 2 else AppDimens.minTouchTarget)
                        .padding(
                            horizontal = AppDimens.spacingMd,
                            vertical = AppDimens.spacingSm
                        ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar/Icon
                Box(
                    modifier = Modifier
                        .size(AppDimens.avatarSizeMedium)
                        .clip(RoundedCornerShape(AppDimens.radiusLg))
                        .background(
                            when {
                                error != null ->
                                    MaterialTheme
                                        .colorScheme
                                        .errorContainer

                                isFilled ->
                                    MaterialTheme
                                        .colorScheme
                                        .primaryContainer

                                else ->
                                    MaterialTheme
                                        .colorScheme
                                        .surfaceVariant
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector =
                            if (error != null) Icons.Default.Error
                            else Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(AppDimens.iconSizeSmall),
                        tint =
                            when {
                                error != null ->
                                    MaterialTheme.colorScheme
                                        .error

                                isFilled ->
                                    MaterialTheme.colorScheme
                                        .primary

                                else ->
                                    MaterialTheme.colorScheme
                                        .onSurfaceVariant
                                        .copy(alpha = 0.6f)
                            }
                    )
                }

                Spacer(Modifier.width(AppDimens.spacingSm))

                // Text content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (isFilled) {
                        // Contact name
                        Text(
                            text = contactName,
                            style =
                                MaterialTheme.typography.bodyLarge
                                    .copy(
                                        fontWeight =
                                            FontWeight
                                                .Medium
                                    ),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                        // Phone number
                        if (contactPhoneNumber.isNotBlank()) {
                            Text(
                                text = contactPhoneNumber,
                                style =
                                    MaterialTheme.typography
                                        .bodySmall,
                                color =
                                    MessageLaterTheme
                                        .extendedColors
                                        .textMuted,
                                maxLines = 1
                            )
                        }
                    } else {
                        // Placeholder
                        Text(
                            text = "Pick a contact",
                            style = MaterialTheme.typography.bodyLarge,
                            color =
                                MessageLaterTheme.extendedColors
                                    .textMuted
                        )
                    }
                }

                // Right action
                if (isFilled) {
                    // Clear button
                    IconButton(
                        onClick = onClear,
                        modifier = Modifier.size(AppDimens.spacingXxl)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear contact",
                            modifier = Modifier.size(AppDimens.iconSizeSmall),
                            tint =
                                MaterialTheme.colorScheme
                                    .onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Error message
        if (error != null) {
            Row(
                modifier = Modifier.padding(top = AppDimens.spacingSm, start = AppDimens.spacingXs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


@Composable
fun MessageInputField(message: String, error: String?, onMessageChange: (String) -> Unit) {
    Column {
        Text(
            text = "Message",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = AppDimens.spacingXs)
        )

        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            placeholder = { Text("Happy birthday! Hope you're doing well 🎉") },
            isError = error != null,
            modifier = Modifier.fillMaxWidth().height(AppDimens.size120),
            shape = RoundedCornerShape(AppDimens.radiusMd),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
        )

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(
                    top = AppDimens.spacingXxs,
                    start = AppDimens.spacingXxs
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPickerDialog(
    contacts: List<Contact>,
    isLoading: Boolean = false,
    onContactSelected: (Contact) -> Unit,
    onDismiss: () -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    val filteredContacts = remember(contacts, searchQuery) {
        if (searchQuery.isBlank()) {
            contacts
        } else {
            contacts.filter { contact ->
                contact.name.contains(searchQuery, ignoreCase = true) ||
                        contact.phoneNumbers.any { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(topStart = AppDimens.spacingXl, topEnd = AppDimens.spacingXl),
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacingSm)
        ) {
            ContactPickerHeader(onClose = onDismiss)
            ContactSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.padding(horizontal = AppDimens.spacingMd)
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = AppDimens.borderWidth
            )

            when {
                filteredContacts.isEmpty() -> {
                    EmptyState(
                        message = if (searchQuery.isNotBlank()) "No contacts found for \"$searchQuery\""
                        else "No contacts available"
                    )
                }

                else -> {
                    ContactList(
                        contacts = filteredContacts,
                        onContactClick = onContactSelected
                    )
                }
            }
        }
    }
}


@Composable
private fun ContactPickerHeader(onClose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = AppDimens.spacingLg, vertical = AppDimens.spacingLg)
    ) {
        // Title
        Text(
            text = "Select Contact",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Center)
        )

        // Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.CenterEnd).size(AppDimens.avatarSizeMedium)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
private fun ContactSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.spacingMd),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = AppDimens.spacingLg, vertical = AppDimens.spacingMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(AppDimens.iconSizeSmall),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )

            Spacer(Modifier.width(AppDimens.spacingMd))

            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = "Search contacts...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle =
                        MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Clear button
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") },
                    modifier = Modifier.size(AppDimens.iconSizeMedium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        modifier = Modifier.size(AppDimens.spacingLg),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


@Composable
private fun ContactList(contacts: List<Contact>, onContactClick: (Contact) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = AppDimens.spacingSm)
    ) {
        items(items = contacts, key = { it.id }) { contact ->
            ContactListItem(contact = contact, onClick = { onContactClick(contact) })
        }
    }
}


@Composable
private fun ContactListItem(contact: Contact, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = AppDimens.spacingLg, vertical = AppDimens.spacingMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            ContactAvatar(name = contact.name)

            Spacer(Modifier.width(AppDimens.spacingLg))

            // Contact info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(AppDimens.spacingXxs))
                Text(
                    text = contact.phoneNumbers[0],
                    style = MaterialTheme.typography.bodySmall,
                    color = MessageLaterTheme.extendedColors.textMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
private fun ContactAvatar(
    name: String,
    modifier: Modifier = Modifier
) {
    val initials =
        remember(name) {
            name.split(" ")
                .take(2)
                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                .joinToString("")
        }

    val backgroundColor =
        remember(name) {
            // Generate consistent color based on name
            val colors =
                listOf(
                    0xFF6366F1, // Indigo
                    0xFF8B5CF6, // Violet
                    0xFFEC4899, // Pink
                    0xFFF59E0B, // Amber
                    0xFF10B981, // Emerald
                    0xFF3B82F6, // Blue
                    0xFFEF4444, // Red
                    0xFF14B8A6 // Teal
                )
            colors[name.hashCode().mod(colors.size)]
        }

    Box(
        modifier =
            modifier.size(AppDimens.avatarSizeMedium)
                .clip(CircleShape)
                .background(Color(backgroundColor)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.White
        )
    }
}


@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(AppDimens.spacingXl),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Box(
                modifier =
                    Modifier.size(AppDimens.radiusXl * 2)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(AppDimens.spacingXl),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }

            Spacer(Modifier.height(AppDimens.spacingXxl))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MessageLaterTheme.extendedColors.textMuted
            )
        }
    }
}


@Composable
fun DateInputField(
    date: String,
    error: String?,
    onClick: () -> Unit,
    modifier: Modifier
) {
    InputCard(
        modifier = modifier,
        label = "Date",
        value = date.ifEmpty { "Select" },
        icon = Icons.Default.DateRange,
        error = error,
        onClick = onClick,
    )
}

@Composable
fun TImeInputField(
    time: String,
    error: String?,
    onClick: () -> Unit,
    modifier: Modifier
) {
    InputCard(
        modifier = modifier,
        label = "Time",
        value = time.ifEmpty { "Select" },
        icon = Icons.Default.Timer,
        error = error,
        onClick = onClick,
    )
}


@Composable
private fun InputCard(
    label: String,
    value: String,
    icon: ImageVector,
    error: String?,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            shape = RoundedCornerShape(AppDimens.radiusMd),
            color = MaterialTheme.colorScheme.surface,
            border =
                BorderStroke(
                    AppDimens.borderWidth,
                    if (error != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.outline
                ),

            ) {
            Column(modifier = Modifier.padding(AppDimens.spacingMd)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(AppDimens.iconSizeSmall),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(AppDimens.spacingSm))
                    Text(
                        text = label,
                        style =
                            MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(AppDimens.spacingSm))
                Text(
                    text = value,
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = AppDimens.spacingXs)
            )
        }
    }
}


@Composable
fun BottomBar(
    state: CreateReminderStates,
    onSaveCLick: () -> Unit
) {
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.surface
    ) {

        Column(
            modifier = Modifier.padding(vertical = AppDimens.spacingLg),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(bottom = AppDimens.spacingMd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(AppDimens.iconSizeSmall / 1.5f),
                    tint = MessageLaterTheme.extendedColors.textMuted
                )

                Spacer(Modifier.width(AppDimens.spacingXs))
                Text(
                    text = "Message are sent only when you tap send",
                    style = MaterialTheme.typography.labelSmall,
                    color = MessageLaterTheme.extendedColors.textMuted
                )
            }

            Button(
                onClick = onSaveCLick,
                modifier = Modifier.fillMaxWidth().height(AppDimens.buttonHeight)
                    .padding(horizontal = AppDimens.screenPaddingHorizontal),
                shape = RoundedCornerShape(AppDimens.radiusMd),
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(AppDimens.iconSizeMedium),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = AppDimens.spacingXxs
                    )
                } else {
                    Text(
                        text = "Save Reminder",
                        style = AppTextStyles.button
                    )
                }
            }
        }
    }
}


@Composable
fun RepeatOptionsSection(
    selectedOption: RepeatOption,
    isPro: Boolean,
    onOptionClick: (RepeatOption) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.spacingSm)) {
        Text(
            text = "Repeat",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        RepeatOptionButton(
            label = "Repeat Yearly",
            isLocked = !isPro,
            onClick = { onOptionClick(RepeatOption.YEARLY) },
            isSelected = selectedOption == RepeatOption.YEARLY
        )

        RepeatOptionButton(
            label = "Repeat Monthly",
            isLocked = !isPro,
            onClick = { onOptionClick(RepeatOption.MONTHLY) },
            isSelected = selectedOption == RepeatOption.MONTHLY
        )
    }
}

@Composable
private fun RepeatOptionButton(
    label: String,
    isSelected: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    // Determine colors based on state
    val backgroundColor =
        when {
            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            isLocked -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.surface
        }

    val borderColor =
        when {
            isSelected -> MaterialTheme.colorScheme.primary
            isLocked -> MaterialTheme.colorScheme.outlineVariant
            else -> MaterialTheme.colorScheme.outline
        }

    val contentColor =
        when {
            isSelected -> MaterialTheme.colorScheme.primary
            isLocked -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.onSurface
        }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimens.radiusMd),
        color = backgroundColor,
        border =
            BorderStroke(
                width = AppDimens.borderWidth,
                color = borderColor
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(AppDimens.cardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Repeat,
                    contentDescription = null,
                    modifier = Modifier.size(AppDimens.iconSizeMedium),
                    tint = contentColor
                )
                Spacer(Modifier.width(AppDimens.spacingSm))
                Text(
                    text = label,
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                    color = contentColor
                )
            }

            // Right side: PRO badge or checkmark
            when {
                isLocked -> ProBadge()
                isSelected -> {
                    // Checkmark when selected
                    Text(
                        text = "✓ Active",
                        style =
                            MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ProBadge() {
    Surface(
        color = MessageLaterTheme.extendedColors.warningContainer,
        shape = RoundedCornerShape(AppDimens.radiusFull)
    ) {
        Text(
            text = "PRO",
            style =
                MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
            color = MessageLaterTheme.extendedColors.onWarningContainer,
            modifier = Modifier.padding(
                horizontal = AppDimens.spacingSm,
                vertical = AppDimens.spacingXs
            )
        )
    }
}