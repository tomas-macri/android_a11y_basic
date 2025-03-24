package com.tomasmacri.accessibilitybasicviews.utils

import android.os.Build
import android.view.View
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat


/**
 * Set if view is a heading for a section of content for accessibility purposes.
 * Users of some accessibility services can choose to navigate between headings instead of between paragraphs, words, etc. Apps that provide headings on sections of text can help the text navigation experience.
 *
 */
fun View.setAccessibilityHeading() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        isAccessibilityHeading = true
    } else {
        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.isHeading = true
            }
        })
    }
}


/**
 * Sets how to determine whether this view is important for accessibility which is if it fires accessibility events and if it is reported to accessibility services that query the screen.
 * Params:
 *
 * Values available:
 * View.IMPORTANT_FOR_ACCESSIBILITY_YES: The view is important for accessibility.
 * View.IMPORTANT_FOR_ACCESSIBILITY_NO: The view is not important for accessibility.
 * View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS: The view is not important for accessibility, nor are any of its descendant views.
 * View.IMPORTANT_FOR_ACCESSIBILITY_AUTO: Automatically determine whether a view is important for accessibility.
 *
 */

fun View.setImportanceForAccessibility(importance: Int) {
    importantForAccessibility = importance
}


/***
 *
 * Sets the live region mode for this view. This indicates to accessibility services whether they should automatically notify the user about changes to the view's content description or text, or to the content descriptions or text of the view's children (where applicable).
 * Different priority levels are available:
 *
 * View.ACCESSIBILITY_LIVE_REGION_POLITE: Indicates that updates to the region should be presented to the user. Suitable in most cases for prominent updates within app content that don't require the user's immediate attention.
 * View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE: Indicates that updates to the region have the highest priority and should be presented to the user immediately. This may result in disruptive notifications from an accessibility service, which may potentially interrupt other feedback or user actions, so it should generally be used only for critical, time-sensitive information.
 * View.ACCESSIBILITY_LIVE_REGION_NONE: Disables change announcements (the default for most views).
 *
 * Examples:
 *
 * Selecting an option in a dropdown menu updates a panel below with the updated content. This panel may be marked as a live region with ACCESSIBILITY_LIVE_REGION_POLITE to notify users of the change. A screen reader may queue changes as announcements that don't disrupt ongoing speech.
 * An emergency alert may be marked with ACCESSIBILITY_LIVE_REGION_ASSERTIVE to immediately inform users of the emergency.
 *
 *
 */
fun View.setLiveRegion(liveRegion: Int) {
    accessibilityLiveRegion = liveRegion
}