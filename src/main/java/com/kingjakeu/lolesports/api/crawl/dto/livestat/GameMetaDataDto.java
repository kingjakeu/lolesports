package com.kingjakeu.lolesports.api.crawl.dto.livestat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameMetaDataDto {
    private String patchVersion;
    private TeamMetaDataDto blueTeamMetadata;
    private TeamMetaDataDto redTeamMetadata;
}
