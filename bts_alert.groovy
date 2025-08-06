let(
  status, prop("상태"),
  dueDate, if(empty(prop("수정일")), false, prop("수정일")),
  releaseDate, if(empty(prop("배포일")), false, prop("배포일")),
  today, today(),

  daysToDue, if(dueDate != false, dateBetween(dueDate, today, "days"), false),
  daysToRelease, if(releaseDate != false, dateBetween(releaseDate, today, "days"), false),

  workingDays, lets(
    startDay, if(hour(now()) < 18, today, dateAdd(today, 1, "days")),
    endDay, if(empty(prop("수정일")), prop("배포일"), prop("수정일")),

    totalDays, dateBetween(endDay, startDay, "days") + 1,
    fullWeeks, floor(totalDays / 7),
    remainingDays, mod(totalDays, 7),

    startWeekday, day(startDay),
    weekendExtra,
        if(startWeekday <= 6 and startWeekday + remainingDays > 6, 1, 0)
        + if(startWeekday + remainingDays > 7, 1, 0),
    totalDays - fullWeeks * 2 - weekendExtra
  ),

  if(status == "이슈 백로그" or status == "릴리즈" or status == "Closed", "",

    if(status == "수정 요청" or status == "재확인요청" or status == "수정중",
      if(dueDate == false, "🟡 수정일 입력 필요",
        if(daysToDue == 0, "🟡 오늘 완료 예정",
          if(daysToDue > 0, "🟢 워킹데이 약 " + workingDays + "일 남음",
            "🔴 상태 갱신 필요(수정일 초과)"
          )
        )
      ),

    if(status == "QA 요청" or status == "QA중",
      if(releaseDate == false,
        if(dueDate == false, "🟡 수정일 입력 필요",
          if(daysToDue == 0, "🟡 오늘 완료 예정",
            if(daysToDue > 0, "🟢 워킹데이 약 " + workingDays + "일 남음",
              "🔴 상태 갱신 필요(수정일 초과)"
            )
          )
        ),
        if(daysToRelease == 0, "🟡 오늘 배포 예정",
          if(daysToRelease > 0, "🟢 " + daysToRelease + "일 후 배포 예정",
            "🟡 상태 갱신 필요(배포일 초과)"
          )
        )
      ),

    if(status == "QA 완료",
      if(releaseDate == false, "🟡 배포일 입력 필요",
        if(daysToRelease == 0, "🟡 오늘 배포 예정",
          if(daysToRelease > 0, "🟢 " + daysToRelease + "일 후 배포 예정",
            "🟡 상태 갱신 필요(배포일 초과)"
          )
        )
      ),

    "⚫️ 상태 갱신 필요(예외 발생)"
    ))))
)