package com.github.sanctum.economy.construct.entity;
/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
class BungeeEntityImpl extends EnterpriseEntityImpl implements BungeeEntity {
    BungeeEntityImpl(@Namespace String namespace, @IdentityKey String identityKey) {
        super(namespace, identityKey);
    }
}
