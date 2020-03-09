package studio.forface.covid.data

import studio.forface.covid.data.local.localDataModule
import studio.forface.covid.data.remote.remoteDataModule

val dataModule = remoteDataModule + localDataModule
