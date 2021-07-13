# Unofficial Riot Esports API Guide

## All League Info

```
GET https://esports-api.lolesports.com/persisted/gw/getLeagues?hl={LangCode-NatCode}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## All Tournament Info

```
GET https://esports-api.lolesports.com/persisted/gw/getTournamentsForLeague?hl={LangCode-NatCode}&leagueId={leagueId}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## All Scheduled Match Info by League

```
GET https://esports-api.lolesports.com/persisted/gw/getSchedule?hl={LangCode-NatCode}&leagueId={leagueId}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## All Completed Match Info by Tournament
```
GET https://esports-api.lolesports.com/persisted/gw/getCompletedEvents?hl=ko-KR&tournamentId={tournamentId}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## Match Detail Info (vod, winner, side)
```
GET https://esports-api.lolesports.com/persisted/gw/getEventDetails?hl=ko-KR&id={matchId}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## Tournament Standing

```
GET https://esports-api.lolesports.com/persisted/gw/getStandings?hl=ko-KR&tournamentId={tournamentId}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## Team Info By League

```
GET https://esports-api.lolesports.com/persisted/gw/getTeams?hl=ko-KR&id={leagueId}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## Team Info By Team slug

```
GET https://esports-api.lolesports.com/persisted/gw/getTeams?hl=ko-KR&id={teamSlug}
x-api-key: 0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z
```

## Game Details

```
GET https://acs.leagueoflegends.com/v1/stats/game/{gameSubId}/{gameTempId}?gameHash={gameHash}
Cookie: id_token={id_token}
```