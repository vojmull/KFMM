using MessengerApi.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Linq;
using System.Web;

namespace MessengerApi
{
    [DbConfigurationType(typeof(MySql.Data.EntityFramework.MySqlEFConfiguration))]
    public class dbContext : DbContext
    {
        public DbSet<FriendshipRequests> FriendshipRequests { get; set; }
        public DbSet<Friendships> Friendships { get; set; }
        public DbSet<Conversations> Conversations { get; set; }
        public DbSet<ChatGroups> ChatGroups { get; set; }
        public DbSet<Messages> Messages { get; set; }
        public DbSet<MessagesEdit> MessagesEdit { get; set; }
        public DbSet<Users> Users { get; set; }
        public DbSet<Participants> Participants { get; set; }
        public DbSet<FriendshipMembers> FriendshipMembers { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
        }
    }

}