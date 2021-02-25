using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class UserContext: DbContext
    {
        public UserContext(DbContextOptions<UserContext> options)
                    : base(options)
        {

        }

        public DbSet<User> Users { get; set; }
        public DbSet<Tasks> Tasks { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Tasks>()
              .HasOne(u => u.User)
              .WithMany(t => t.Tasks)
              .HasForeignKey(u => u.UserId)
              .HasConstraintName("ForeignKey_Tasks_Users");

        }
    }
}
