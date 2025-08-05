let(
  status, prop("상태"),
  startDate, if(empty(prop("시작일")), false, prop("시작일")),
  dueDate, if(empty(prop("완료일")), false, prop("완료일")),
  releaseDate, if(empty(prop("배포일")), false, prop("배포일")),
  today, today(),

  daysToStart, if(startDate != false, dateBetween(startDate, today, "days"), false),
  daysToDue, if(dueDate != false, dateBetween(dueDate, today, "days"), false),
  daysToRelease, if(releaseDate != false, dateBetween(releaseDate, today, "days"), false),

  workingDays, prop("workingDayLeft"),

  if(status == "작업 백로그" or status == "릴리즈" or status == "작업완료", "",

    if(status == "작업대기",
      if(startDate == false, "",
        if(daysToStart == 0, "🟢 오늘 시작 예정",
          if(daysToStart > 0, "🔵 " + daysToStart + "일 후 시작 예정",
            "🟡 상태 갱신 필요(시작일 초과)"
          )
        )
      ),

    if(status == "작업중",
      if(dueDate == false, "🟡 완료일 입력 필요",
        if(daysToDue == 0, "🟡 오늘 완료 예정",
          if(daysToDue > 0, "🟢 워킹데이 약 " + workingDays + "일 남음",
            "🔴 상태 갱신 필요(작업일 초과)"
          )
        )
      ),

    if(status == "재확인요청" or status == "리뷰중" or status == "QA 요청" or status == "QA 중",
      if(releaseDate == false,
        if(dueDate == false, "🟡 완료일 입력 필요",
          if(daysToDue == 0, "🟡 오늘 완료 예정",
            if(daysToDue > 0, "🟢 워킹데이 약 " + workingDays + "일 남음",
              "🔴 상태 갱신 필요(작업일 초과)"
            )
          )
        ),
        if(daysToRelease == 0, "🟡 오늘 배포 예정",
          if(daysToRelease > 0, "🟢 " + daysToRelease + "일 후 배포 예정",
            "🟡 상태 갱신 필요(배포일 초과)"
          )
        )
      ),

    "⚫️ 상태 갱신 필요(예외 발생)"
    ))))
)