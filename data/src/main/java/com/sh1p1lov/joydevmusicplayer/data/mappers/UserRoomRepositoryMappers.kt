package com.sh1p1lov.joydevmusicplayer.data.mappers

import com.sh1p1lov.joydevmusicplayer.data.room.entities.UserEntity
import com.sh1p1lov.joydevmusicplayer.domain.models.RegistrationUserParams
import com.sh1p1lov.joydevmusicplayer.domain.models.UserInfo

fun mapToUserInfoList(userEntities: List<UserEntity>): List<UserInfo> {
    val userInfoList = mutableListOf<UserInfo>()
    for (userEntity in userEntities) userInfoList.add(mapToUserInfo(userEntity))
    return userInfoList
}

fun mapToUserInfo(userEntity: UserEntity): UserInfo {
    return UserInfo(
        username = userEntity.username,
        email = userEntity.email,
        passwordHashCode = userEntity.password.hashCode()
    )
}

fun mapToUserInfoOrNull(userEntity: UserEntity?): UserInfo? {
    return if (userEntity == null) null else mapToUserInfo(userEntity)
}

fun mapToUserEntity(userParams: RegistrationUserParams): UserEntity {
    return UserEntity(
        username = userParams.username,
        email = userParams.email,
        password = userParams.password
    )
}