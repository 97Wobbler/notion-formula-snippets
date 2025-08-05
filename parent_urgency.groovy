let(
  status, prop("상태"),
  startDate, if(empty(prop("시작일")), false, prop("시작일")),
  dueDate, if(empty(prop("완료일")), false, prop("완료일")),
  releaseDate, if(empty(prop("배포일")), false, prop("배포일")),
  today, today(),

  daysToStart, if(startDate != false, dateBetween(startDate, today, "days"), false),
  daysToDue, if(dueDate != false, dateBetween(dueDate, today, "days"), false),
  daysToRelease, if(releaseDate != false, dateBetween(releaseDate, today, "days"), false),

  if(status == "백로그" or status == "기디 완료" or status == "릴리즈", "E",

    if(status == "작업 대기",
      if(startDate == false, "E",
        if(daysToStart == 0, "C",
          if(daysToStart > 0, "D", "B")
        )
      ),

    if(status == "작업중",
      if(dueDate == false or daysToDue == 0, "B",
        if(daysToDue > 0, "C", "A")
      ),

    if(status == "리뷰중" or status == "QA 요청" or status == "QA 중" or status == "배포 대기",
      if(releaseDate == false,
        if(dueDate == false or daysToDue == 0, "B",
          if(daysToDue > 0, "C", "A")
        ),
        if(daysToRelease == 0, "B",
          if(daysToRelease > 0, "C", "B")
        )
      ),

    "A"
    ))))
)