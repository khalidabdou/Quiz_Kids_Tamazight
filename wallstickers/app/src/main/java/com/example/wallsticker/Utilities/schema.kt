package com.example.wallsticker.Utilities

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "tb_quotes"
        const val COLUMN_NAME_QUOTE = "quote"
    }

    object FeedEntryImage : BaseColumns {
        const val TABLE_NAME = "tb_images"
        const val COLUMN_NAME_IMAGE = "image"
    }
}