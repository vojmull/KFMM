using MessengerApi.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MessengerApi.Controllers
{
    public class ValuesController : ApiController
    {
        public dbContext _database = new dbContext();

        [HttpGet]
        [Route("api/test")]
        public bool Test()
        {
            try
            {
                Test t = this._database.Test.ToList().FirstOrDefault();
            }
            catch
            {
                return false;
            }

            return true;
        }

        // GET api/values
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}
