using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class Tasks
    {

        [Key]
        public Guid TaskId { get; set; }
        [Required]
        public DateTime CreatedDate { get; set; }
        [Required]
        [MaxLength(150,ErrorMessage = "Too long content!")]
        public string Content { get; set; }

        [Required]
        public Guid UserId { get; set; }
        [JsonIgnore]
        public User User { get; set; }

    }
}
