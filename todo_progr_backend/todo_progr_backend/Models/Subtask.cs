using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class Subtask
    {
        [Key]
        public Guid SubtaskId { get; set; }
        [Required]
        public DateTime CreatedDate { get; set; }
        [Required]
        [MaxLength(150, ErrorMessage = "Too long Subtask!")]
        public string Content { get; set; }

        public Task MainTask { get; set; }
    }
}
