using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Controllers
{
    public class SubtasksController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
