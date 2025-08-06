let(
  status, prop("상태"),
  dueDate, if(empty(prop("수정일")), false, prop("수정일")),
  releaseDate, if(empty(prop("배포일")), false, prop("배포일")),
  today, today(),

  daysToDue, if(dueDate != false, dateBetween(dueDate, today, "days"), false),
  daysToRelease, if(releaseDate != false, dateBetween(releaseDate, today, "days"), false),

  if(status == "이슈 백로그" or status == "릴리즈" or status == "Closed", "E",

    if(status == "수정 요청" or status == "재확인요청" or status == "수정중",
      if(dueDate == false or daysToDue == 0, "B",
        if(daysToDue > 0, "C", "A")
      ),

    if(status == "QA 요청" or status == "QA중",
      if(releaseDate == false,
        if(dueDate == false or daysToDue == 0, "B",
          if(daysToDue > 0, "C", "A")
        ),
        if(daysToRelease == 0, "B",
          if(daysToRelease > 0, "C", "B")
        )
      ),

    if(status == "QA 완료",
      if(releaseDate == false or daysToRelease == 0, "B",
        if(daysToRelease > 0, "C", "B")
      ),

    "A"
    ))))
)