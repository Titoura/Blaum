package com.titou.blaum.data_source.remote.titles_api.requests;

import com.titou.blaum.data_source.remote.titles_api.api.TitlesApiService
import com.titou.blaum.use_cases.RemoteAlbumsSource
import org.koin.core.KoinComponent


class RemoteTitlesDataSource(
    private val titlesApiService: TitlesApiService
) : KoinComponent, RemoteAlbumsSource {}

