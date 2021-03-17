using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class User
    {
        public User()
        {
            Tasks = new List<Tasks>();
        }
        [Key]
        public Guid UserId { get; set; }
        [Required]
        [MinLength(5, ErrorMessage = "Too short User Name!")]
        [MaxLength(50, ErrorMessage = "Too long User Name!")]                                                                                                                     
        public string UserName { get; set; }
        [Required]
        [MinLength(8, ErrorMessage = "Too short Password!")]
        [MaxLength(50, ErrorMessage = "Too long Password!")]
        public string Password { get; set; }
        [Required]
        [MaxLength(70, ErrorMessage = "Too long Email!")]
        public string Email { get; set; }

        public List<Tasks> Tasks { get; set; }

    }
}
